xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")

xml.data() {
	records.each { Site s ->
		site(id: s.id) {
			modifiedOn(s.modifiedOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
			createdOn(s.createdOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
			drivingDirections(s.drivingDirections)
			isAdministrationCentre(s.isAdministrationCentre)
			latitude(s.latitude?.toBigDecimal()?.setScale(8)?.toPlainString())
			longitude(s.longitude?.toBigDecimal()?.setScale(8)?.toPlainString())
			name(s.name)
			postcode(s.postcode)
			publicTransportDirections(s.publicTransportDirections)
			state(s.state)
			street(s.street)
			suburb(s.suburb)

			s.rooms.each { Room r ->
				room(id: r.id) {
					modifiedOn(r.modifiedOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
					createdOn(r.createdOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"))
					directions(r.directions)
					facilities(r.facilities)
					name(r.name)
					notes(r.notes)
					seatedCapacity(r.seatedCapacity)
				}
			}
		}

	}
}
