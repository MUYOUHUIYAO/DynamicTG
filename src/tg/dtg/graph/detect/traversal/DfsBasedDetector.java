package tg.dtg.graph.detect.traversal;

import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import tg.dtg.cet.EventTrend;
import tg.dtg.graph.AttributeVertex;
import tg.dtg.graph.EventVertex;
import tg.dtg.graph.construct.Constructor;
import tg.dtg.graph.detect.Detector;
import tg.dtg.query.Predicate;
import tg.dtg.query.Query;

public class DfsBasedDetector extends Detector {

  private final Comparator<EventVertex> cmp = Ordering.natural().onResultOf(EventVertex::timestamp);
  public DfsBasedDetector(ArrayList<EventVertex> eventVertices,
      ArrayList<Constructor> constructors, Query query, boolean isWrite) {
    super(eventVertices, constructors, query, isWrite);
  }

  private long count = 0;
  @Override
  public void detect() {
    if(isWrite) {
      outputFunc = (et) -> count += 1;
    }
    prefilter();
    System.out.println(starts.size());
    long start = System.currentTimeMillis();
    Predicate predicate = query.condition.predicates().values().iterator().next();
    for(EventVertex ev: starts) {
      EventTrend trend = new EventTrend();
      doDFS(predicate.tag, ev, trend);
      //System.out.println(ev.timestamp());
    }
    System.out.println(outputs.size());
    long end = System.currentTimeMillis();
    System.out.println("time " + (end - start) + " count " + count);
  }

  public HashSet<EventVertex> doDFS(char c, EventVertex vertex, EventTrend trend) {
    HashSet<EventVertex> isVisit = new HashSet<>();
    trend.append(vertex.event);

    if(ends.contains(vertex)) {
      outputFunc.accept(trend);
      isVisit.add(vertex);
      return isVisit;
    }
    PriorityQueue<EventVertex> nextLayer = new PriorityQueue<>(cmp);
    for (AttributeVertex av : vertex.getEdges().get(c)) {
      for (EventVertex ev : av.getEdges()) {
        if (ev.timestamp() > vertex.timestamp()) {
          nextLayer.offer(ev);
        }
      }
    }

    while (!nextLayer.isEmpty()){
      EventVertex ev = nextLayer.poll();
      if(isVisit.contains(ev)) continue;
      EventTrend newTrend = trend.copy();
      HashSet<EventVertex> visited = doDFS(c, ev, newTrend);
      isVisit.addAll(visited);
    }
    isVisit.add(vertex);
    return isVisit;
  }
}