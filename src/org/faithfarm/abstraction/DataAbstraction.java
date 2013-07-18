package org.faithfarm.abstraction;

import java.util.List;
import java.util.Map;

public abstract interface DataAbstraction
{
  public abstract List<DAObject> get(String paramString, Map<String, String> paramMap, Map<String, Integer> paramMap1);
}

/* Location:           C:\development\workspace\faithfarmoss\WEB-INF\classes\
 * Qualified Name:     org.faithfarm.abstraction.DataAbstraction
 * JD-Core Version:    0.6.2
 */