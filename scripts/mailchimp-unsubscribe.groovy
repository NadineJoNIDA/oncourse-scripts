def run(args) {
	def tagRelation = args.entity

	if (getIntegration(tagRelation.tag.name) && NodeSpecialType.MAILING_LISTS.equals(tagRelation.tag?.parentTag?.specialType) && tagRelation.taggedContact.email) {
		mailchimp {
			name tagRelation.tag.name
			action "unsubscribe"
			email tagRelation.taggedContact.email
		}
	}
}
