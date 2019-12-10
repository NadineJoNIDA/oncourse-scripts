import ish.oncourse.server.cayenne.Invoice
import ish.oncourse.server.cayenne.InvoiceLine
import ish.oncourse.server.cayenne.Voucher
import ish.oncourse.server.cayenne.VoucherProduct
import ish.util.ProductUtil
import ish.util.SecurityUtil
import org.apache.cayenne.query.SQLTemplate

def today = new Date()
today.set(hourOfDay: 0, minute: 0, second: 0)

def birthdayProdSku = "birthday"

def query = new SQLTemplate(Contact.class, "SELECT * FROM Contact WHERE DAYOFYEAR(BIRTHDATE) BETWEEN DAYOFYEAR(NOW())+1 AND DAYOFYEAR(NOW())+7")
def contacts = args.context.performQuery(query)


VoucherProduct product = ObjectSelect.query(VoucherProduct)
        .where(VoucherProduct.SKU.eq(birthdayProdSku))
        .selectOne(args.context)

contacts.each { c ->

    //must create an invoice/invoiceline for voucher validation
    Invoice invoice = args.context.newObject(Invoice)
    invoice.contact = c
    invoice.amountOwing = Money.ZERO
    invoice.source = PaymentSource.SOURCE_ONCOURSE
    invoice.confirmationStatus = ConfirmationStatus.DO_NOT_SEND

	InvoiceLine invoiceLine = args.context.newObject(InvoiceLine)
    invoiceLine.invoice = invoice
    invoiceLine.account = product.liabilityAccount
    invoiceLine.tax = product.tax
    invoiceLine.title = product.name
    invoiceLine.priceEachExTax = Money.ZERO
    invoiceLine.taxEach = Money.ZERO
    invoiceLine.discountEachExTax = Money.ZERO
    invoiceLine.quantity = 1
    invoiceLine.description = "${c.getName(true)} (${product.sku} ${product.name})"

    // Creates the voucher from the product
    Voucher voucher = args.context.newObject(Voucher)
    voucher.product = product
    voucher.redeemableBy = c
    voucher.code = SecurityUtil.generateVoucherCode()
    voucher.source = PaymentSource.SOURCE_ONCOURSE
    voucher.status = ProductStatus.ACTIVE
    voucher.invoiceLine = invoiceLine
    voucher.redemptionValue = product.value
    voucher.valueOnPurchase = product.value
    voucher.redeemedCourseCount = 0
    voucher.expiryDate = ProductUtil.calculateExpiryDate(ProductUtil.getToday(), product.expiryType, product.expiryDays)
    voucher.confirmationStatus = ConfirmationStatus.DO_NOT_SEND

    email {
        to c
        template "Birthday voucher email"
        bindings contact: c, voucher: voucher
    }
}
args.context.commitChanges()