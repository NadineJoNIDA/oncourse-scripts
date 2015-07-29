xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")

records.each { TrainingPackage tp ->
	csv << [
			"nationalISC": tp.nationalISC,
			"title"      : tp.title,
			"modifiedOn" : tp.modifiedOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"),
			"createdOn"  : tp.createdOn?.format("yyyy-MM-dd'T'HH:mm:ssZ")
	]
}
