package ru.nsu.ccfit.melon.app

import ru.nsu.ccfit.melon.app.tool.SceneSettingTool
import ru.nsu.ccfit.melon.core.ui.getIntegerInput
import ru.nsu.ccfit.melon.core.ui.Button
import ru.nsu.ccfit.melon.core.ui.PlumFrame
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.GridLayout
import javax.swing.JPanel


class SettingFrame(context: Context) : PlumFrame(width = 800, height = 800, title = SceneSettingTool.tooltip) {
    private val context: Context
    private lateinit var pointsPanel: PointsPanel

    init {
        this.context = context
        init()
    }

    private fun apply() {
        context.parameters.points = pointsPanel.scenePoints
        context.parameters.splineBasePoints = pointsPanel.basePoints
        cancel()
    }

    private fun init() {
        val sp = context.parameters
        val all = JPanel(GridBagLayout())
        val c = GridBagConstraints()
        pointsPanel = PointsPanel(sp)
        c.fill = GridBagConstraints.BOTH
        c.gridx = 0
        c.gridy = 0
        c.weightx = 1.0
        c.weighty = 5.0
        all.add(pointsPanel, c)
        c.fill = GridBagConstraints.VERTICAL
        c.gridx = 0
        c.gridy = 1
        c.weighty = 0.0
        c.weightx = 0.0

        val controls = JPanel(GridLayout(1, 4, 0, 0))
        controls.add(getIntegerInput("Количество ребер поворота", sp.angleN, 2, 100, 1) { sp.angleN = it })
        controls.add(
            getIntegerInput(
                "Количество невидимых ребер поворота",
                sp.virtualAngleN,
                1,
                100,
                1
            ) { sp.virtualAngleN = it }
        )
        controls.add(getIntegerInput("Количество точек сплайма", sp.splineN, 1, 100, 1) { v ->
            sp.splineN = v
            pointsPanel.repaint()
        })
        c.fill = GridBagConstraints.BOTH
        c.gridx = 0
        c.gridy = 2
        c.weighty = 0.5
        c.weightx = 0.0
        all.add(controls, c)
        val buttons = JPanel(GridLayout(1, 4, 0, 0))
        buttons.add(Button("Принять") { apply() })
        buttons.add(Button("Отмена") { cancel() })
        c.fill = GridBagConstraints.BOTH
        c.gridx = 0
        c.gridy = 3
        c.weighty = 0.0
        c.weightx = 0.0
        all.add(buttons, c)
        add(all)
    }

    private fun cancel() {
        isVisible = false
    }
}
