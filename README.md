## DynamicTG
A Complete Event Trend (CET) detection system.

## Introduction
*Complete Event Trend* (CET) detection over large-scale event streams is important but challenging in various applications such as financial services, real-time business analysis, and supply chain management. A potential large number of partial intermediate results during complex event matching raises prohibitively high memory cost for the processing system. The state-of-the-art design leverages compact graph encoding, which represents the common sub-sequences of different complex events using a common sub-graph to achieve space efficiency for storing the intermediate results. However, we show that such a design raises unacceptable computation cost for the graph traversal needed whenever a new event comes. To address this problem, in this paper, we propose a novel *attribute-based indexing* (ABI) graph model to represent the relationship between events. By classifying the predicates and constructing the graph based on both the comparators in the predicates and the attribute values of the events, we achieve parallel event streams processing and efficient graph construction. Our design significantly reduces the computation cost of graph construction to $O(m)$ for each event, where $m$ is the size of the attribute vertices in the graph. We further design an efficient traversal-based algorithm to extract CETs from the graph. We conduct comprehensive experiments to evaluate the performance of this design. The results show that our design wins a couple of orders of magnitude back from state-of-the-art schemes.

### dependencies
```
  com.beust:jcommander:1.78
  com.google.guava:guava:28.1-jre
  com.typesafe:config:1.4.0
```
We didn't use a build tool like maven or gradle to manage dependencies. Therefore, you can either use the IDE to manage these dependencies or download jar files directly.
### usage
Currently we mainly implement two examples in the package `tg.dtg.main.examples`. You can run these examples with the command:
```bash
java -jar $jarfile $type [options]
```
in which `$type$` is the name of example.

For other applications, you can extend `tg.dtg.main.examples.Example` and customize by yourself.

### Options
`-i file`

the event input file.

`-wl wl,-sl sl`

the window length and slide length

`-p parallism`

how much threads are used. `-1` means use sequential algorithm.

`-sel step`

the step length for select anchors. Default is 2. If `step < 0`, the extraction processs is omitted.

`-pdfs`

[Optional] Whether a parallel dfs-based method is applied.

`-w dir`

[Optional] the directory that the edges and vertices are written to. For debug.

`-out`

[Optional] if set, the final CETs result will be written to the directory that are set by `-w`


