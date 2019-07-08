
package controllers.front;

import controllers.Controller;
import managers.DealsManager;
import models.Deal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class DealsController extends Controller {

	private static final int PAGINATION = 12;
    /**
     Creates a controller, usually called from servlet, which is also
     passed by parameter. Servlet method passes taken request and response.
     */
    public DealsController(HttpServletRequest request,
    				        HttpServletResponse response,
    				         HttpServlet servlet) {
        super(request, response, servlet);
    }

    /**
     Searches deals with some criteria.
     Creates List of Deal objects (found by some criteria)
	 separates found deals in pages according to page param and
     dispatches list to /pages/public/deals.jsp
     */
    public void index(SearchCriteria sc) throws ServletException, IOException {
    	String pageParam = request.getParameter("page");
    	int page = 1;
		if(pageParam != null && !pageParam.isEmpty() && pageParam.matches("[0-9]+")) {
			page = Integer.parseInt(pageParam);
			if(page <= 0) page = 1;
		}
		List<Deal> deals = DealsManager.getDeals(sc);

		if(deals.size() < (page-1)*PAGINATION){
			sendError(404, "No deals on page "+page);
			return;
		}
		List<Deal> paginatedDeals;

		if(deals.size() < page*PAGINATION)
			paginatedDeals = deals.subList((page-1)*PAGINATION, deals.size());
		else
			paginatedDeals = deals.subList((page-1)*PAGINATION, page*PAGINATION);

		//attributes to setup pagination on the frontend
		setPaginationAttributes(deals.size(), page);

		request.setAttribute("deals", paginatedDeals);
		dispatchTo("/pages/public/deals.jsp");
    }

	/**
	 * Given a size of the collection and current page number
	 * Sets request attributes for paginating on the frontend
	 * @param collectionSize size of the retrieved collection to paginate
	 * @param page current page number
	 */
    private void setPaginationAttributes(int collectionSize, int page){
		String next_page_url = (collectionSize < page*PAGINATION)? null : "/deals?page="+(page+1);
		String prev_page_url = (page == 1) ? null : "/deals?page="+(page-1);

		int last_page_num = collectionSize/PAGINATION + 1;

		request.setAttribute("next_page_url", next_page_url);
		request.setAttribute("prev_page_url", prev_page_url);
		request.setAttribute("curr_page_num", page);
		request.setAttribute("last_page_num", last_page_num);
	}

	/**
	 Inner class for encapsulation search criteria
 	 */
	public static class SearchCriteria {

		/**
		 Enumeration of criteria,
		 which user can use for searching.
		 */
		public enum Criteria {

			/* Possible Criteria */
			USER_NAME("user-name"),
			CATEGORY_NAME("category-name"),
			DEAL_CREATE_DATE("deal-create-date"),
			DEAL_UPDATE_DATE("deal-update-date");

			private String criteriaName;

			/**
			 Private constructor for enumerated criteria.
			 */
			Criteria(String criteriaName) {
				this.criteriaName = criteriaName;
			}

			/**
			 Returns name of the criteria.
			 */
			public String getCriteriaName() {
				return criteriaName;
			}

			/**
			 Static method which
			 Returns Criteria object of specific name.
			 If the one does not exists returns null.
			 */
			public static Criteria getCriteria(String criteriaName) {
				for (Criteria aCriteria : values())
					if (aCriteria.getCriteriaName().equals(criteriaName))
						return aCriteria;
				return null;
			}
		}

		private Map<Criteria, String> criteria = new HashMap<>();

		/**
		 Adds new criteria with specific value to the criteria list.
 		 */
		public void addCriteria(Criteria aCriteria, String value) {
			criteria.put(aCriteria, value);
		}

		/**
		 Returns iterator of criteria
		 */
		public Iterator<Criteria> getCriteriaIterator() {
			return criteria.keySet().iterator();
		}

		/**
		 Returns value of specific criteria.
		 If the one does not exists in criteria list, returns null.
		 */
		public String getCriteriaValue(Criteria aCriteria) {
			return criteria.get(aCriteria);
		}
	}

    /**
     Sets attributes for displaying some deal and
     dispatches to /pages/public/deal.jsp
     */
    public void show(int dealID) throws ServletException, IOException {

    	Deal deal = DealsManager.getDealByDealID(dealID);

    	if (deal == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Deal with given id wasn't found!");
			return;
		}

		request.setAttribute("deal", deal);
		dispatchTo("/pages/public/deal.jsp");
    }
}
