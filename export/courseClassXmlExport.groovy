xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")

def courseClasses = records.groupBy { CourseClass cc -> cc.course }

xml.data() {
	courseClasses.each { Course co, List<CourseClass> classes ->
		course(id: co.id) {
			modifiedOn(co.modifiedOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
			createdOn(co.createdOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
			allowWaitingLists(co.allowWaitingLists)
			code(co.code)
			currentlyOffered(co.currentlyOffered)
			fieldOfEducation(co.fieldOfEducation)
			isWebVisible(co.isShownOnWeb)
			isSufficientForQualification(co.isSufficientForQualification)
			isVET(co.isVET)
			name(co.name)
			printedBrochureDescription(co.printedBrochureDescription)
			reportableHours(co.reportableHours)
			webDescription(co.webDescription)
			classes.each { cc ->
				courseClass(id: cc.id) {
					modifiedOn(cc.modifiedOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
					createdOn(cc.createdOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
					code(cc.code)
					deliveryMode(cc.deliveryMode?.displayName)
					endDateTime(cc.endDateTime?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
					price() {
						amount(cc.feeIncGst?.toPlainString())
						taxAmount(cc.feeGST?.toPlainString())
						taxName(cc.tax?.taxCode ?: "unknown")
					}
					cc.discountCourseClasses.collect { DiscountCourseClass dcc -> dcc.discount }.each { Discount d ->
						price() {
							def taxRate = cc.tax ? cc.tax.rate : new BigDecimal(0)
							amount(DiscountUtils.getDiscountedFee(Arrays.asList(d), cc.feeIncGst, taxRate).toPlainString())
							taxAmount(DiscountUtils.getDiscountedFee(Arrays.asList(d), cc.feeIncGst, taxRate).multiply(taxRate))
							taxName(cc.tax?.taxCode ?: "unknown")
							discountAmount(DiscountUtils.discountValue(Arrays.asList(d), cc.feeIncGst, taxRate).toPlainString())
							discountName(d.name)
						}
					}
					cost {
						incomeMaximum(ish.budget.ClassBudgetUtil.getClassIncomeExTax(cc, ish.budget.ClassBudgetUtil.MAXIMUM)?.toPlainString())
						incomeBudget(ish.budget.ClassBudgetUtil.getClassIncomeExTax(cc, ish.budget.ClassBudgetUtil.BUDGETED)?.toPlainString())
						incomeActual(ish.budget.ClassBudgetUtil.getClassIncomeExTax(cc, ish.budget.ClassBudgetUtil.ACTUAL)?.toPlainString())
						expensesMaximum(ish.budget.ClassBudgetUtil.getClassCostsExTax(cc, ish.budget.ClassBudgetUtil.MAXIMUM)?.toPlainString())
						expensesBudget(ish.budget.ClassBudgetUtil.getClassCostsExTax(cc, ish.budget.ClassBudgetUtil.BUDGETED)?.toPlainString())
						expensesActual(ish.budget.ClassBudgetUtil.getClassCostsExTax(cc, ish.budget.ClassBudgetUtil.ACTUAL)?.toPlainString())
						profitMaximum(ish.budget.ClassBudgetUtil.getClassProfitExTax(cc, ish.budget.ClassBudgetUtil.MAXIMUM)?.toPlainString())
						profitBudget(ish.budget.ClassBudgetUtil.getClassProfitExTax(cc, ish.budget.ClassBudgetUtil.BUDGETED)?.toPlainString())
						profitActual(ish.budget.ClassBudgetUtil.getClassProfitExTax(cc, ish.budget.ClassBudgetUtil.ACTUAL)?.toPlainString())
					}
					fundingSource(cc.fundingSource?.displayName)
					isCancelled(cc.isCancelled)
					isDistantLearningCourse(cc.isDistantLearningCourse)
					isWebVisible(cc.isShownOnWeb)
					maximumPlaces(cc.maximumPlaces)
					message(cc.message)
					minimumPlaces(cc.minimumPlaces)
					budgetedPlaces(cc.budgetedPlaces)
					notes(cc.notes)
					startDateTime(cc.startDateTime?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
					webDescription(cc.webDescription)
					accountCode(cc.incomeAccount?.accountCode)

					cc.enrolments.each { Enrolment e ->
						enrolment(id: e.id) {
							modifiedOn(e.modifiedOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
							createdOn(e.createdOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
							source(e.source?.displayName)
							status(e.status?.displayName)
							vetClientID(e.vetClientID)
							contact(id: e.student.contact.id)
							e.tags.each { Tag t ->
								tag(t.pathName)
							}
							e.outcomes.each { Outcome o ->
								outcome(id: o.id) {
									modifiedOn(o.modifiedOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
									createdOn(o.createdOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
									startDate(o.startDate?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
									endDate(o.endDate?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
									status(o.status?.displayName)
									module(nationalCode: o.module?.nationalCode)
								}
							}
						}
					}

					cc.sessions.each { Session s ->
						session(id: s.id) {
							modifiedOn(s.modifiedOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
							createdOn(s.createdOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
							start(s.startDatetime?.format("yyyy-MM-dd'T'HH:mm:ssZ", s.timeZone))
							end(s.endDatetime?.format("yyyy-MM-dd'T'HH:mm:ssZ", s.timeZone))
							s.tutors.each { Tutor tr ->
								tutor(id: tr.id) {
									firstName(tr.contact.firstName)
									lastName(tr.contact.lastName)
								}
							}
							if (s.room) {
								room(id: s.room.id) {
									name(s.room.name)
									site(id: s.room.site.id) {
										name(s.room.site.name)
									}
								}
							}

						}

					}
					
				}
			}
		}
	}
}