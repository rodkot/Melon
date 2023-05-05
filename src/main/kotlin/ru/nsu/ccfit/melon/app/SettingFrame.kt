package ru.nsu.ccfit.melon.app

import ru.nsu.ccfit.melon.core.ui.getIntegerInput
import ru.nsu.ccfit.melon.core.ui.makeClickableButton
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel


// Parameters
// k - число опорных точек
// N - отрезков для участка (между опорными точками)
// M - число образующих
// M1 - число отрезков для участка на отк-тях
class SettingFrame(context: Context) : JFrame("Edit scene parameters") {
    private val context: Context
    private lateinit var pointsPanel: PointsPanel

    init {
        minimumSize = Dimension(640, 480)
        this.context = context
        init()
    }

    private fun apply() {
        context.parameters.points = pointsPanel.scenePoints
        context.parameters.splineBasePoints = pointsPanel.basePoints
    }

    private fun submit() {
        apply()
        isVisible = false
    }

    private fun reset() {
        pointsPanel.reset()
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
        all.add(JLabel("Use left mouse button to add points and right mouse button to remove them"), c)
        val controls = JPanel(GridLayout(1, 4, 0, 0))
        controls.add(getIntegerInput("Rot angle count", sp.angleN, 2, 100, 1) { sp.angleN = it })
        controls.add(
            getIntegerInput(
                "Virtual rot angle count",
                sp.virtualAngleN,
                1,
                100,
                1
            ) { sp.virtualAngleN = it }
        )
        controls.add(getIntegerInput("Spline points count", sp.splineN, 1, 100, 1) { v ->
            sp.splineN = v
            pointsPanel.repaint()
        })
        //        controls.add(UIUtils.getDoubleInput("Z Far", sp.getFar(), 0, 1000, 0.1, sp::setFar));
//        controls.add(UIUtils.getDoubleInput("Z Near", sp.getNear(), 0, 1000, 0.1, sp::setNear));
        c.fill = GridBagConstraints.BOTH
        c.gridx = 0
        c.gridy = 2
        c.weighty = 0.5
        c.weightx = 0.0
        all.add(controls, c)
        val buttons = JPanel(GridLayout(1, 4, 0, 0))
        buttons.add(makeClickableButton("Submit") { submit() })
        buttons.add(makeClickableButton("Apply") { apply() })
        buttons.add(makeClickableButton("Remove all points") { reset() })
        c.fill = GridBagConstraints.BOTH
        c.gridx = 0
        c.gridy = 3
        c.weighty = 0.0
        c.weightx = 0.0
        all.add(buttons, c)
        add(all)
    }
}
