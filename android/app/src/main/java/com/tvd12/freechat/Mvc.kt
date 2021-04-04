package com.tvd12.freechat

/**
 * Created by tavandung12 on 10/7/18.
 */

class Mvc private constructor() {
    private val controllers: MutableMap<Any, Any>

    init {
        this.controllers = HashMap()
        this.addController("connection")
        this.addController("contact")
        this.addController("message")
    }

    companion object {
        private val INSTANCE = Mvc()
        fun getInstance() : Mvc = INSTANCE
    }

    private fun addController(name: String) : Controller {
        val controller = Controller()
        addController(name, controller)
        return controller
    }

    private fun addController(name: String, controller: Controller) {
        this.controllers[name] = controller
    }

    fun getController(name: String) : Controller {
        return controllers[name] as Controller
    }
}

class Controller {
    private val views: MutableMap<String, MutableMap<String, IView>>

    init {
        views = HashMap()
    }

    fun addView(action: String, view: IView) {
        addView(action, "", view)
    }

    fun addView(action: String, viewId : String, view: IView) {
        views.computeIfAbsent(action) {
            HashMap()
        }[viewId] = view
    }

    fun removeView(action: String) {
        removeView(action, "")
    }

    fun removeView(action: String, viewId: String) {
        views[action]?.remove(viewId)
    }

    fun updateViews(action: String, data: Any?) {
        views[action]?.keys?.forEach {
            updateView(action, it, data)
        }
    }

    private fun updateView(action: String, viewId: String, data: Any?) {
        val available = views[action]
        if(available != null) {
            available[viewId]?.update(viewId, data)
        }
    }

}

interface IView {

    fun update(viewId: String, data: Any?)
}