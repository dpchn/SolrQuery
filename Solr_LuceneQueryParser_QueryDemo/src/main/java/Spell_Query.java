import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.Hash;

public class Spell_Query {

	void SpellCheck(HttpSolrClient client) {
		try {
			SolrQuery query = new SolrQuery();
			query.setRequestHandler("/spell");
			query.add("q", "sanui");
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("qt", "/spell");
			//params.set("q", "i wan to buy washin machin");
			params.set("q", "northlantic");
			params.set("spellcheck", "true");
			params.set("spellcheck.build", "true");
			QueryResponse queryResponse = client.query(params);
			
			
			System.out.println(queryResponse);

			System.out.println(queryResponse.getSuggesterResponse());
			// System.out.println(queryResponse.getSuggesterResponse().getSuggestedTerms());
			printResult(queryResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void printResult(QueryResponse response) {

		SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();

		/*for (Object str : spellCheckResponse.getSuggestions())
			System.out.println(str);*/

		SolrDocumentList list = response.getResults();
		if (!spellCheckResponse.isCorrectlySpelled()) {
			
			for (Suggestion suggestion : spellCheckResponse.getSuggestions()) {
				
				System.out.println("Original Frequency : "+suggestion.getOriginalFrequency());
				System.out.println("Alternatives Frequency : "+suggestion.getAlternativeFrequencies());
				System.out.println("No. found : "+suggestion.getNumFound());
				System.out.println("Original Frequency : "+suggestion.getStartOffset());
				System.out.println("original token: " + suggestion.getToken() + " - alternatives: "
						+ suggestion.getAlternatives());
			}
		}

		for (SolrDocument doc : list) {
			for (String str : doc.getFieldNames()) {
				System.out.println(str + " : " + doc.getFieldValue(str));
			}

			System.out.println();
		}
		
	}


}
