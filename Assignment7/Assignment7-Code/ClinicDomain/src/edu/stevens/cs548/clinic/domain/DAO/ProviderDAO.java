package edu.stevens.cs548.clinic.domain.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.stevens.cs548.clinic.domain.Provider;


public class ProviderDAO implements IProviderDAO{
	@PersistenceContext
	private EntityManager em;
	private TreatmentDAO treatmentDAO;
	
	public ProviderDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO=new TreatmentDAO(em);
	}
	
	@Override
	public Provider getProviderByDbId(long Id) throws ProviderExp {
		// TODO Auto-generated method stub
		Provider p = em.find(Provider.class, Id);
		if (p == null) {
			throw new ProviderExp("Provider Not Found: primary key=" + Id);
		} else {
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
	}
	@Override
	public Provider getProviderByNPI(long NPI) throws ProviderExp {
		// TODO Auto-generated method stub
		Provider p = em.find(Provider.class, NPI);
		if (p == null) {
			throw new ProviderExp("Provider Not Found: primary key=" + NPI);
		} else {
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
	}

	@Override
	public List<Provider> getProviderByName(String name) {
		// TODO Auto-generated method stub
		TypedQuery<Provider> query = em.createNamedQuery("SearchProviderByName", Provider.class)
				.setParameter("name", name);
		List<Provider>providers=query.getResultList();
		for(Provider p:providers)
		{
			p.setTreatmentDAO(this.treatmentDAO);
		}
		return providers;		
	}

	@Override
	public void addProvider(Provider provider) throws ProviderExp {
		// TODO Auto-generated method stub
		long NPI=provider.getNPI();
		TypedQuery<Provider>query=em.createNamedQuery("SearchProviderByProviderNPI", Provider.class)
				.setParameter("NPI", NPI);
		List<Provider>providers=query.getResultList();
		if(providers.size()<1){
			em.persist(provider);
			provider.setTreatmentDAO(this.treatmentDAO);
		}
		else {
			Provider provider2=providers.get(0);
			throw new ProviderExp("Insertion: Provider with NPI("+NPI+") already exists.\n** Name :"+provider2.getName());
		}
	}

	@Override
	public void deleteProvider(Provider provider) throws ProviderExp {
		// TODO Auto-generated method stub
		/*em.createQuery("delete from treatment t where t.provider.id= :NPI")
		.setParameter("NPI", provider.getNPI())
		.executeUpdate();
		*/
		em.remove(provider);
	}
	
}
