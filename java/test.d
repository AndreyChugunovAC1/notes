module test;

import std;
import std.datetime.stopwatch;









void main() {
  int i;
  StopWatch sw = StopWatch(AutoStart.yes);
  generate!(() => i++).take(100_000).to!string.writeln();
  sw.stop();
  auto first = sw.peek;

  sw = StopWatch(AutoStart.yes);
  generate!(() => i++).take(100_000).writeln();
  sw.stop();
  writeln(first);
  writeln(sw.peek());
}

















