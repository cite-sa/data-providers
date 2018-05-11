The metadata prefix that our implementation supports is oai_dc. 

OAIPMH over the earthserver's catalogue manager supports the following operations/verbs:
1)	GetRecord: It returns the coverage with the given identifier (coverage id); the coverage is transformed 
	in the metadataPrefix (i.e. oai_dc) format. We converts a coverage to a dublin core items
	using an xslt transformation <links>.

2)	ListRecords: If there is no 'set' attribute specified, it will return all the records/coverages 
	from the catalogue. On the other hand, the 'set' attribute specifies the endpoint from which the 
	coverages will be retrieved. Harvesting records with datetime values is not supported because coverages do not 
	have a timestamp element or attribute. 
	
3)	ListSets: returns the endpoints of the catalogue.

4)	ListMetadataFormats: returns the metadata formats that support the catalogue. In our case, it's only "oai_dc". 

5)	Identify: returns information about the catalogue/database. 'earliestDatestamp' is null due to the fact 
	that coverages do not support timestamp.

In addition, resumptionTokens are not supported in our implementation because baseX repository does not, too.