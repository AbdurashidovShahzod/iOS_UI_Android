package uz.shahzod.unzosoft.iosui_android.ui.iosDialog

import java.io.Serializable

/**
 * Created by Abdurashidov Shahzod on 26/12/22
 * company QQBank
 * shahzod9933@gmail.com
 */

class IOSDialogButton : Serializable {
    var id: Int
        private set
    var text: String
        private set
    var isMakeBold = false
        private set
    var type = 0
        private set

    constructor(id: Int, text: String) {
        this.id = id
        this.text = text
    }

    constructor(id: Int, text: String, makeBold: Boolean, type: Int) {
        this.id = id
        this.text = text
        isMakeBold = makeBold
        this.type = type
    }

    companion object {
        const val TYPE_POSITIVE = 1
        const val TYPE_NEGATIVE = 2
    }
}