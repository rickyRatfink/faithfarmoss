package org.faithfarm.price;

import java.util.Map;

public abstract interface PriceQuery
{
  public abstract Map<String, String> getItems();

  public abstract String doQuery();
}

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.price.PriceQuery
 * JD-Core Version:    0.6.2
 */