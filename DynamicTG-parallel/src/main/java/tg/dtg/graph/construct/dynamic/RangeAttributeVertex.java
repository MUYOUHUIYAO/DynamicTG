package tg.dtg.graph.construct.dynamic;

import com.google.common.collect.Range;
import java.util.ArrayList;
import java.util.List;
import tg.dtg.common.values.NumericValue;
import tg.dtg.graph.AttributeVertex;
import tg.dtg.graph.EventVertex;

public class RangeAttributeVertex implements AttributeVertex, Comparable<RangeAttributeVertex> {

  protected final ArrayList<EventVertex> vertices;
  protected Range<NumericValue> range;

  public RangeAttributeVertex(
      Range<NumericValue> range) {
    this.range = range;
    vertices = new ArrayList<>();
  }

  public void linkToEvent(EventVertex eventVertex) {
    vertices.add(eventVertex);
  }

  public Range<NumericValue> getRange() {
    return range;
  }

  public void setRange(Range<NumericValue> range) {
    this.range = range;
  }

  @Override
  public List<EventVertex> getEdges() {
    return vertices;
  }

  @Override
  public String toString() {
    return "RangeAttributeVertex{"
        + "range=" + range
        + '}';
  }

  @Override
  public int compareTo(RangeAttributeVertex o) {
    return range.lowerEndpoint().compareTo(o.range.lowerEndpoint());
  }

  @Override
  public String shortString() {
    return range.toString();
  }
}
