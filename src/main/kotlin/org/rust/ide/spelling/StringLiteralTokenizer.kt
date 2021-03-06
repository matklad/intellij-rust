package org.rust.ide.spelling

import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.spellchecker.inspections.PlainTextSplitter
import com.intellij.spellchecker.tokenizer.EscapeSequenceTokenizer
import com.intellij.spellchecker.tokenizer.TokenConsumer
import org.rust.lang.utils.parseRustStringCharacters

object StringLiteralTokenizer : EscapeSequenceTokenizer<LeafPsiElement>() {

    override fun tokenize(element: LeafPsiElement, consumer: TokenConsumer) {
        val text = element.text

        if ("\\" !in text) {
            consumer.consumeToken(element, PlainTextSplitter.getInstance())
        } else {
            processTextWithEscapeSequences(element, text, consumer)
        }
    }

    private fun processTextWithEscapeSequences(element: LeafPsiElement, text: String, consumer: TokenConsumer) {
        val unescapedText = StringBuilder()
        val offsets = IntArray(text.length + 1)
        parseRustStringCharacters(text, unescapedText, offsets)
        processTextWithOffsets(element, consumer, unescapedText, offsets, 0)
    }
}
