package ru.nsu.ccfit.melon.core.ui

import java.awt.event.ActionListener
import java.util.function.Consumer
import javax.swing.*
import javax.swing.JSpinner.DefaultEditor
import javax.swing.event.ChangeEvent

fun Button(label: String, onClick: ActionListener): JButton {
    val btn = JButton(label)
    btn.addActionListener(onClick)
    return btn
}

fun getIntegerInput(
    label: String,
    default: Int,
    min: Int,
    max: Int,
    minorTickSpacing: Int,
    onChange: (Int)->Unit
): JPanel {
    val result = JPanel()
    result.add(JLabel(label))
    val spinner = JSpinner(SpinnerNumberModel(default, min, max, minorTickSpacing))
    spinner.editor = JSpinner.NumberEditor(spinner, "#")
    spinner.addChangeListener { event: ChangeEvent ->
        val value = (event.source as JSpinner).value as Int
        onChange.invoke(value)
    }
    result.add(spinner)
    return result
}