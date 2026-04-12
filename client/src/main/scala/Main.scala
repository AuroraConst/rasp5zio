import org.aurora.configuration.program

import org.aurora.*
import zio._

object Main extends ZIOAppDefault :
  //this is how to use schedule combinator and repeat in zio
  val s =  Schedule.count.whileOutput(_ < 50)  .tapOutput(o => Console.printLine(s"Count: $o").orDie)  

  def run = 
    (lineprogram.program *>
     client.program1 *>
    client.program).repeat(s) *>
    lineprogram.program


    lineprogram.program 



 