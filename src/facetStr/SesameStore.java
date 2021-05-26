package facetStr;

import java.io.File;
import java.io.IOException;

import org.openrdf.model.Resource;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.memory.MemoryStore;


public class SesameStore {
	
	
	private Repository repo;
	RepositoryConnection store;

	public SesameStore()  {
		this.repo = new SailRepository(new MemoryStore());
		try{
			repo.initialize();
			store = repo.getConnection();
		} catch (RepositoryException e){
		}
	}
	
	
	public void loadData(File file) {
		try{
			if(file != null && file.exists())
				store.add(file, null, RDFFormat.N3);
		} catch (RepositoryException | RDFParseException | IOException e){
		}
	}

	public StoreAdapter executeQuery(String query) {
		StoreAdapter result = null;
		try{
			TupleQuery tQ = store.prepareTupleQuery(QueryLanguage.SPARQL, query);
			result = new StoreAdapter(tQ.evaluate());
		} catch (RepositoryException | MalformedQueryException | QueryEvaluationException e){
			System.out.println(query + e);
		}
		return result;
	}

}
