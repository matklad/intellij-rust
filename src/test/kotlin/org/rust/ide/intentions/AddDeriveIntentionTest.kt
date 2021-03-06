package org.rust.ide.intentions

import org.rust.lang.RustTestCaseBase

class AddDeriveIntentionTest : RustTestCaseBase() {
    override val dataPath = "org/rust/ide/intentions/fixtures/add_derive/"

    fun testAddDeriveStruct() = checkByFile {
        openFileInEditor("add_derive_struct.rs")
        myFixture.launchAction(AddDeriveIntention())
    }

    fun testAddDerivePubStruct() = checkByFile {
        openFileInEditor("add_derive_pub_struct.rs")
        myFixture.launchAction(AddDeriveIntention())
    }

    fun testAddDeriveEnum() = checkByFile {
        // FIXME: there is something weird with enum re-formatting, for some reason it adds more indentation
        openFileInEditor("add_derive_enum.rs")
        myFixture.launchAction(AddDeriveIntention())
    }

    fun testAddDeriveExistingAttr() = checkByFile {
        openFileInEditor("add_derive_existing_attr.rs")
        myFixture.launchAction(AddDeriveIntention())
    }
}
