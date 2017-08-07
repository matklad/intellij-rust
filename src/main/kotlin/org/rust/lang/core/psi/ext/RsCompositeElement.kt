/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.lang.core.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement
import org.rust.cargo.project.workspace.CargoWorkspace
import org.rust.cargo.project.workspace.cargoWorkspace
import org.rust.lang.core.psi.RsFile
import org.rust.lang.core.resolve.ref.RsReference

interface RsCompositeElement : PsiElement {
    override fun getReference(): RsReference?
}


val RsCompositeElement.crateRoot: RsMod? get() {
    return if (this is RsFile) {
        val root = superMods.lastOrNull()
        if (root != null && root.isCrateRoot)
            root
        else
            null
    } else {
        (context as? RsCompositeElement)?.crateRoot
    }
}

val RsCompositeElement.containingCargoTarget: CargoWorkspace.Target? get() {
    val cargoProject = module?.cargoWorkspace ?: return null
    val root = crateRoot ?: return null
    val file = root.containingFile.originalFile.virtualFile ?: return null
    return cargoProject.findTargetForCrateRootFile(file)
}

val RsCompositeElement.containingCargoPackage: CargoWorkspace.Package? get() = containingCargoTarget?.pkg

abstract class RsCompositeElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), RsCompositeElement {
    override fun getReference(): RsReference? = null
}

abstract class RsStubbedElementImpl<StubT : StubElement<*>> : StubBasedPsiElementBase<StubT>, RsCompositeElement {

    constructor(node: ASTNode) : super(node)

    constructor(stub: StubT, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    override fun getReference(): RsReference? = null

    override fun toString(): String = "${javaClass.simpleName}($elementType)"
}
