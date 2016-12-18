import patmat.Huffman._

val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)

val str: String = "scala"
val chrs: List[Char] = string2Chars(str)
val ts = times(chrs)

val sing = List(t1)
val notsing = List(t1,t2)

val ol = makeOrderedLeafList(times(chrs))

val t  = createCodeTree(chrs)

