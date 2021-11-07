package adapter;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import business_logic.BLFacade;
import domain.Bet;
import domain.Client;

public class ClientBetAdapter extends AbstractTableModel {
	
	private transient List<Bet> bets;
	
	public ClientBetAdapter(Client client, BLFacade blf) {
		this.bets = blf.getClientBets(client);
	}
	
	@Override
	public int getRowCount() {
		return bets.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Bet bet = bets.get(rowIndex);
		if(columnIndex == 0) {
			return bet.getRaceHorse().getRace().getNumOfStreets();
		}else if(columnIndex == 1) {
			return bet.getRaceHorse().getHorse().getName();
		}else if(columnIndex == 2) {
			return bet.getRaceHorse().getRace().getDate();
		}else if(columnIndex == 3) {
			return bet.getAmount();
		}else {
			return null;
		}
	}
}


