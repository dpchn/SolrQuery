import java.util.Scanner;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Indexing indexing = new Indexing();
		AddData addData = new AddData();
		SolrServer startServer = new SolrServer();
		HttpSolrClient client = startServer.StartSolrServer();
		TypesOfQuery typesOfQuery = new TypesOfQuery();
		SortingInSolr sortingInSolr = new SortingInSolr();
		FacetQuery facetQuery = new FacetQuery();
		Spell_Query  spell = new Spell_Query();
		AutoSuggestSpell autoSuggestSpell = new AutoSuggestSpell();
		AutoSuggestionNEdgegram suggestionNEdgegram = new AutoSuggestionNEdgegram();
		while(true) {
			System.out.println("1 : Inser data");
			System.out.println("2 : Full Indexing");
			System.out.println("3 : Delta Indexing");
			System.out.println("4 : Query data");
			System.out.println("5 : Sorting");
			System.out.println("6 : Facet Query");
			System.out.println("7 : Spell Checker Query");
			System.out.println("8 : Auto suggestion Query");
			System.out.println("9 : Auto suggestion using N edge gram Query");
			System.out.println("10 : Exit");
			System.out.println("Enter your choice....");
			int ch = scanner.nextInt();
			switch (ch) {
			case 1:
				addData.Add();
				break;
			case 2:
				indexing.FullImport(client);
				break;

			case 3:
				indexing.DeltaImport();
				break;
			case 4:
				typesOfQuery.QueryOption(client);
				break;
			case 5:
				sortingInSolr.sortAsc(client);
				break;
			case 6:
				facetQuery.allFacetQuery(client);
				break;
			case 7:
				spell.SpellCheck(client);
				break;
			case 8:
				autoSuggestSpell.autoSuggest(client);
				break;
			case 9:
				suggestionNEdgegram.autoSuggest(client);
				break;

			case 10:
				System.exit(0);
				
			}
			
		}
		
	}
}
