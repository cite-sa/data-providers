package gr.cite.oaipmh.repository;

import javax.inject.Inject;

/**
 * 
 * @author Ioannis Kavvouras
 * 
 */
public class RepositoryConnectionFactory {
	
	@Inject
	private static Repository repository;

	private RepositoryConnectionFactory() {
	}

	/**
	 * 
	 * @return the registered {@link Repository} of the
	 *         {@link RepositoryConnectionFactory}
	 * @throws RepositoryRegistrationException
	 *             if no {@link Repository} is registered into
	 *             {@link RepositoryConnectionFactory}
	 */
	public static Repository getRepository()
			throws RepositoryRegistrationException {
		if (repository == null) {
			throw new RepositoryRegistrationException(
					"repository is not registered to RepositoryConnectionFactory,"
							+ " call RepositoryConnectionFactory.registerRepository");
		}
		return repository;
	}

	/**
	 * 
	 * @param repository
	 * @throws RepositoryRegistrationException
	 *             if a {@link Repository} is already registered into
	 *             {@link RepositoryConnectionFactory}
	 */
	public static void registerRepository(Repository repository)
			throws RepositoryRegistrationException {
		if (RepositoryConnectionFactory.repository == null) {
			RepositoryConnectionFactory.repository = repository;
		} else {
			throw new RepositoryRegistrationException(
					"Repository is already registered");
		}
	}
}
