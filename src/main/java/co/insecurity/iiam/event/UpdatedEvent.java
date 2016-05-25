package co.insecurity.iiam.event;

public class UpdatedEvent {
  protected boolean entityFound = true;
  protected boolean successful = true;

  public boolean isEntityFound() {
    return entityFound;
  }
  
  public boolean isSuccessful() {
	  return successful;
  }
}
