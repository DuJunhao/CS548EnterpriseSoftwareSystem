package edu.stevens.cs548.clinic.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class ProviderDAO implements IProviderDAO {
	
	private EntityManager em;
	private TreatmentDAO treatmentDAO;

	public ProviderDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}

	@Override
	public long addProvider(Provider prov) throws ProviderExn {
		long NPI = prov.getNPI();
		Query query = em.createNamedQuery("CountProviderByNPI").setParameter("NPI", NPI);
		Long numExisting = (Long) query.getSingleResult();
		if(numExisting <1) {
			prov.setTreatmentDAO(treatmentDAO);
			em.persist(prov);
			return NPI;
		}
		else {
			throw new ProviderExn("Insertion: Provider with NPI (" + NPI + ") already exists.");
		}
	}

	@Override
	public Provider getProvider(long id) throws ProviderExn {
		Provider p = em.find(Provider.class, id);
		if (p == null) {
			throw new ProviderExn("Provider not found: primary key = " + id);
		} else {
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
	}

	@Override
	public Provider getProviderByNPI(long NPI) throws ProviderExn {
		TypedQuery<Provider> query = em.createNamedQuery("SearchProviderByNPI", Provider.class)
				.setParameter("NPI", NPI);
		List<Provider> providers = query.getResultList();
		if (providers.size() > 1)
			throw new ProviderExn("Duplicate provider records: NPI = " + NPI);
		else if (providers.size() < 1) 
			throw new ProviderExn("Provider not found: NPI = " + NPI);
		else {
			Provider p = providers.get(0);
			p.setTreatmentDAO(treatmentDAO);
			return p;
		}
	}

	@Override
	public void deleteProvider(Provider provider) throws ProviderExn {
		em.remove(provider);
	}
}
