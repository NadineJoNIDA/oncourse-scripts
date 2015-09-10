def run(args) {
	def tagRelation = args.entity
	
	if (getIntegration(tagRelation.tag.name) && NodeSpecialType.MAILING_LISTS.equals(tagRelation.tag?.parentTag?.specialType) && tagRelation.taggedContact.email) {
		mailchimp {
			name tagRelation.tag.name
			action "subscribe"
			email tagRelation.taggedContact.email
			firstName tagRelation.taggedContact.firstName
			lastName tagRelation.taggedContact.lastName
		}
	}
}
