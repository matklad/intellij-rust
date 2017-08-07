/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.lang.core.psi.ext

import com.intellij.psi.util.PsiTreeUtil
import org.rust.lang.core.psi.RsModDeclItem


val RsCompositeElement.containingMod: RsMod?
    get() = PsiTreeUtil.getStubOrPsiParentOfType(this, RsMod::class.java)

val RsModDeclItem.containingMod: RsMod
    get() = (this as RsCompositeElement).containingMod
        ?: error("Rust mod decl outside of a module")
