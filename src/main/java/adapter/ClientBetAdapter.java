package adapter;

import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import business_logic.BLFacade;
import domain.Bet;
import domain.Client;

@SuppressWarnings("serial")
public class ClientBetAdapter extends AbstractTableModel {
	
	private transient List<Bet> bets;
	private String[] colNames = {"Race streets", "Horse name", "Race date", "Amount bet"};
	
	public ClientBetAdapter(Client client, BLFacade blf) {
		this.bets = blf.getClientBets(client);
	}
	
	@Override
	public int getRowCount() {
		if(bets == null) {
			return 0;
		}else {
			return bets.size();
		}
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}
	
	@Override 
    public String getColumnName(int col) { 
        return colNames[col]; 
    }
	
	@Override
	public Class<?> getColumnClass(int col) {
	    if (col == 0) {
	        return Integer.class;
	    }else if(col == 1){
	    	return String.class;
	    }else if(col == 2){
	  	    return Date.class;
	    }else {
	    	return Double.class;
	    }
   }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Bet bet = bets.get(rowIndex);
		Object returnObj = null;
		if(columnIndex == 0) {
			returnObj = Integer.valueOf(bet.getRaceHorse().getRace().getNumOfStreets());
		}else if(columnIndex == 1) {
			returnObj = bet.getRaceHorse().getHorse().getName();
		}else if(columnIndex == 2) {
			returnObj = bet.getRaceHorse().getRace().getDate();
		}else if(columnIndex == 3) {
			returnObj = Double.valueOf(bet.getAmount());
		}
		System.out.println(returnObj);
		return returnObj;
	}
}


