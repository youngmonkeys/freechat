package vn.team.freechat_kotlin

/**
 * Created by tavandung12 on 10/7/18.
 */

class Mvc {
    private val controllers: MutableMap<Any, Any>

    private constructor() {
        this.controllers = HashMap()
        this.addController("connection")
        this.addController("contact")
        this.addController("message")
    }

    companion object {
        private val INSTANCE = Mvc()
        fun getInstance() : Mvc = INSTANCE
    }

    fun addController(name: String) : Controller {
        val controller = Controller()
        addController(name, controller)
        return controller
    }

    fun addController(name: String, controller: Controller) {
        this.controllers[name] = controller
    }

    fun getController(name: String) : Controller {
        val controller = controllers[name] as Controller
        return controller
    }
}

class Controller {
    private val views: MutableMap<String, MutableMap<String, IView>>

    constructor() {
        views = HashMap()
    }

    fun addView(action: String, view: IView) {
        addView(action, "", view)
    }

    fun addView(action: String,  viewId : String, view: IView) {
        var available = views[action];
        if(available == null) {
            available = HashMap()
            views[action] = available
        }
        available[viewId] = view
    }

    fun removeView(action: String) {
        removeView(action, "")
    }

    fun removeView(action: String, viewId: String) {
        var available = views[action]
        if(available != null)
            available.remove(viewId)
    }

    fun updateViews(action: String, data: Any?) {
        updateViews(action, "", data)
    }

    fun updateViews(action: String, viewId: String, data: Any?) {
        var available = views[action]
        if(available != null) {
            val view = available[viewId]
            if(view != null)
                view.update(viewId, data)
        }
    }

}

interface IView {

    fun update(viewId: String, data: Any?)

}