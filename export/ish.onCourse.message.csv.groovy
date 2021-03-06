records.each { Message msg ->
    csv << [
            "id"                : msg.id,
            "contacts"          : msg?.contacts*.fullName?.flatten()?.unique()?.join(", "),
            "createdOn"         : msg?.createdOn?.format("d-M-y HH:mm:ss"),
            "createdBy"         : msg?.createdBy?.login  ?: "",
            "emailFrom"         : msg?.emailFrom ?: "",
            "emailSubject"      : msg?.emailSubject ?: "",
            "emailHtmlBody"     : msg?.emailHtmlBody ?: "",
            "emailBody"         : msg?.emailBody ?: "",
            "smsText"           : msg?.smsText ?: "",
            "postDescription"   : msg?.postDescription ?: ""
    ]
}
