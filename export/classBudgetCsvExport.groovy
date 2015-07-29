records.each { CourseClass cc ->
	csv << [
			"Class name"      : cc.course.name,
			"Code"            : "${cc.course.code}-${cc.code}",
			"Start"           : cc.startDateTime?.format("yyyy-MM-dd"),
			"End"             : cc.endDateTime?.format("yyyy-MM-dd"),
			"Enrol maximum"   : cc.maximumPlaces,
			"Enrol budget"    : cc.budgetedPlaces,
			"Enrol actual"    : cc.validEnrolmentCount,
			"Class fee"       : cc.feeIncGst?.toPlainString(),
			"Income maximum"  : ish.budget.ClassBudgetUtil.getClassIncomeExTax(cc, ish.budget.ClassBudgetUtil.MAXIMUM)?.toPlainString(),
			"Income budget"   : ish.budget.ClassBudgetUtil.getClassIncomeExTax(cc, ish.budget.ClassBudgetUtil.BUDGETED)?.toPlainString(),
			"Income actual"   : ish.budget.ClassBudgetUtil.getClassIncomeExTax(cc, ish.budget.ClassBudgetUtil.ACTUAL)?.toPlainString(),
			"Expenses maximum": ish.budget.ClassBudgetUtil.getClassCostsExTax(cc, ish.budget.ClassBudgetUtil.MAXIMUM)?.toPlainString(),
			"Expenses budget" : ish.budget.ClassBudgetUtil.getClassCostsExTax(cc, ish.budget.ClassBudgetUtil.BUDGETED)?.toPlainString(),
			"Expenses actual" : ish.budget.ClassBudgetUtil.getClassCostsExTax(cc, ish.budget.ClassBudgetUtil.ACTUAL)?.toPlainString(),
			"Profit maximum"  : ish.budget.ClassBudgetUtil.getClassProfitExTax(cc, ish.budget.ClassBudgetUtil.MAXIMUM)?.toPlainString(),
			"Profit budget"   : ish.budget.ClassBudgetUtil.getClassProfitExTax(cc, ish.budget.ClassBudgetUtil.BUDGETED)?.toPlainString(),
			"Profit actual"   : ish.budget.ClassBudgetUtil.getClassProfitExTax(cc, ish.budget.ClassBudgetUtil.ACTUAL)?.toPlainString()
	]
}
