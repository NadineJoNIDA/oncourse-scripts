def run(args) {
    def article = args.entity

    if (PaymentSource.SOURCE_WEB.equals(article.invoiceLine.invoice.source)) {
                smtp {
                    to preference.email.admin
                    subject "${article.product.name} purchased"
                    content "Hi \n\n${article.contact.fullName} has just purchased ${article.product.name} on the web \n\nThis is an automated notification from onCourse"
                }
        }
}