def integrations = query {
    entity Integration
    query "type is MICROPOWER"
    context args.context
}

integrations.each { integration ->
    micropower {
        name integration.name
    }
}
