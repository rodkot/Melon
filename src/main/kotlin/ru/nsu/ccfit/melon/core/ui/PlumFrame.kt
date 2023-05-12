package ru.nsu.ccfit.melon.core.ui

import java.awt.BorderLayout
import java.awt.event.ActionListener
import javax.swing.*


open class PlumFrame() : JFrame() {
    private val menuBar: JMenuBar
    private var toolBar: JToolBar

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        menuBar = JMenuBar()
        jMenuBar = menuBar
        toolBar = JToolBar("Main toolbar")
        toolBar.isRollover = true
        add(toolBar, BorderLayout.PAGE_START)


        val iconUrl = Thread.currentThread().contextClassLoader.getResource("icon.png")
        val icon = ImageIcon(iconUrl)
        iconImage = icon.image

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    }

    fun addToolBarButton(menuPath: String, action: ActionListener) {
        val button = JButton(menuPath)
        button.addActionListener(action)
        button.toolTipText = "This is a tooltip"
        toolBar.add(button)
    }

    constructor(width: Int, height: Int, title: String) : this() {
        setSize(width, height)
        isLocationByPlatform = true
        setTitle(title)
    }

}

