package com.jtransc.graph

import com.jtransc.ast.*
import com.jtransc.gen.TargetName
import org.junit.Assert.assertEquals
import org.junit.Test

class RelooperTest {
	val types = AstTypes(TargetName("js"))
	val relooper = Relooper(types)

	private fun stmt(name: String): AstStm = AstType.INT.local(name).setTo(1.lit)

	/*
	@Test fun testIf() {
		val A = relooper.node(stmt("a"))
		val B = relooper.node(stmt("b"))
		relooper.edge(A, B, AstExpr.build { INT.local("a") eq 1.lit })
		Assert.assertEquals("{ a = 1; if ((a == 1)) { b = 1; } NOP }", dump(relooper.render(A)!!.optimize()).toString(doIndent = false).trim())
	}

	@Test fun testIf2() {
		val A = relooper.node(stmt("a"))
		val B = relooper.node(stmt("b"))
		val C = relooper.node(stmt("c"))
		relooper.edge(A, B)
		relooper.edge(A, C, AstExpr.build { INT.local("a") eq 1.lit })
		relooper.edge(C, B)
		Assert.assertEquals("{ a = 1; if ((a == 1)) { b = 1; } NOP }", dump(relooper.render(A)!!.optimize()).toString(doIndent = false).trim())
	}
	*/

	/*
	@Test fun testIfElseNoExplicitEnd() {
		val A = relooper.node(stmt("a"))
		val B = relooper.node(stmt("b"))
		val C = relooper.node(stmt("c"))
		relooper.edge(A, B, AstExpr.build { INT.local("a") eq 1.lit })
		relooper.edge(A, C)
		Assert.assertEquals("{ a = 1; if ((a == 1)) { b = 1; } else { c = 1; } NOP }", dump(relooper.render(A)).toString(doIndent = false).trim())
	}

	@Test fun testIfElseExplicitEnd() {
		val A = relooper.node(stmt("a"))
		val B = relooper.node(stmt("b"))
		val C = relooper.node(stmt("c"))
		val D = relooper.node(stmt("d"))
		relooper.edge(A, B, AstExpr.build { INT.local("a") eq 1.lit })
		relooper.edge(A, C)
		relooper.edge(B, D)
		relooper.edge(C, D)
		Assert.assertEquals("{ a = 1; if ((a == 1)) { b = 1; } else { c = 1; } d = 1; }", dump(relooper.render(A)).toString(doIndent = false).trim())
	}

	@Test fun testDoubleIf() {
		val A = relooper.node(stmt("pre"))
		val B = relooper.node(stmt("if1"))
		val C = relooper.node(stmt("if2"))
		val D = relooper.node(stmt("end"))
		relooper.edge(A, B, AstExpr.build { INT.local("pre") eq 1.lit })
		relooper.edge(B, C, AstExpr.build { INT.local("if1") eq 1.lit })
		relooper.edge(C, D)
		relooper.edge(B, D)
		//relooper.edge(C, D)
		println(dump(relooper.render(A)).toString())
		//Assert.assertEquals("{ a = 1; if ((a == 1)) { b = 1; } else { c = 1; } d = 1; }", dump(relooper.render(A)).toString(doIndent = false).trim())
	}
	*/

	fun String.normalizeMulti() = this.trimIndent().trim().lines().map { it.trimEnd() }.joinToString("\n")

	@Test
	fun testDoubleWhile() {
		// A -> B -> C -> D -> E
		//     /|\   *    |
		//      |_________/
		val A = relooper.node(stmt("A"))
		val B = relooper.node(stmt("B"))
		val C = relooper.node(stmt("C"))
		val D = relooper.node(stmt("D"))
		val E = relooper.node(stmt("E"))
		relooper.edge(A, B)
		relooper.edge(B, C)
		relooper.edge(C, D)
		relooper.edge(D, E)
		relooper.edge(D, B, AstExpr.RAW(AstType.BOOL, "condLoopOutContinue"))
		relooper.edge(D, E, AstExpr.RAW(AstType.BOOL, "condLoopOutBreak"))
		relooper.edge(C, C, AstExpr.RAW(AstType.BOOL, "condLoopInContinue"))
		relooper.edge(C, E, AstExpr.RAW(AstType.BOOL, "condLoopOutBreak"))
		relooper.edge(A, E, AstExpr.RAW(AstType.BOOL, "condToAvoidLoop"))
		val result = relooper.render(A)
		assertEquals("""
			A = 1;
			if (condToAvoidLoop) {
				do {
					B = 1;
					do {
						C = 1;
						if (condLoopInContinue) {
							continue;
						}
						if (condLoopOutBreak) {
							break;
						}
					} while (true);
					D = 1;
					if (condLoopOutContinue) {
						continue;
					}
					if (condLoopOutBreak) {
						break;
					}
				} while (true);
			}
			E = 1;
			NOP(empty stm)
		""".normalizeMulti(), result.dumpCollapse(types).toString().normalizeMulti())
		//println(dump(relooper.render(A)).toString())
		//Assert.assertEquals("{ a = 1; if ((a == 1)) { b = 1; } else { c = 1; } d = 1; }", dump(relooper.render(A)).toString(doIndent = false).trim())
	}
}