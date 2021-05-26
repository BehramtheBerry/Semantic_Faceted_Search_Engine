package facetStr;

import java.util.List;

import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;

public class StoreAdapter {
	
	private TupleQueryResult TQueryResult;
	private List<String> bindingNames;
	private BindingSet bindingSet;
	private boolean hasNext;

	public StoreAdapter(TupleQueryResult tQueryResult) {
		this.TQueryResult = tQueryResult;
		try {
			this.bindingNames = tQueryResult.getBindingNames();
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
		try {
			TQueryResult.close();
		} catch (QueryEvaluationException e){
			e.printStackTrace();
		}
	}

	public boolean hasNext() {
		return hasNext;
	}

	public void next() {
		try {
			hasNext = TQueryResult.hasNext();
			if(hasNext) {
				bindingSet = TQueryResult.next();
			}	
		} catch (QueryEvaluationException e){
			e.printStackTrace();
		}
	}

	public void open() {
		try {
			if(TQueryResult.hasNext()){
				hasNext = true;
				bindingSet = TQueryResult.next();
			} else {
				hasNext = false;
			}
		} catch (QueryEvaluationException e){
			e.printStackTrace();
		}
	}

	public String getItem(int index) {
		String name = bindingNames.get(index);
		return bindingSet.getValue(name).stringValue();
	}
}
