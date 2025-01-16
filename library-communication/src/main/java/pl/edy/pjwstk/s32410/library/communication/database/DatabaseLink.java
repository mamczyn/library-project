package pl.edy.pjwstk.s32410.library.communication.database;

import lombok.Getter;

public abstract class DatabaseLink {
	@Getter private DatabaseLink.Status status = Status.NOT_CONNECTED;
	
	public abstract void connect();
	
	public void reconnect() {
		if(status != Status.CONNECTING) {
			setStatus(Status.CONNECTING);
			connect();
		}
	}
	
	public abstract <D> void store(String key, D data);
	public abstract <D> void retrieve(String key, Class<D> type);
	
	protected void setStatus(Status status) {
		this.status = status;
	}
	
	public abstract boolean contains(String key);
	public boolean isConnected() {
		return status == Status.CONNECTED;
	}
	
	public static enum Status {
		NOT_CONNECTED,
		CONNECTING,
		CONNECTED,
		DISCONNECTING,
		;
	}
}
