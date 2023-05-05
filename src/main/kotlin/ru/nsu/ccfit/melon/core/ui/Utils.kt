package ru.nsu.ccfit.melon.core.ui

import java.awt.event.ActionListener
import java.util.function.Consumer
import javax.swing.*
import javax.swing.JSpinner.DefaultEditor
import javax.swing.event.ChangeEvent

fun makeClickableButton(label: String, onClick: ActionListener): JButton {
    val btn = JButton(label)
    btn.addActionListener(onClick)
    return btn
}

fun getIntegerInput(
    label: String,
    defaultValue: Int,
    min: Int,
    max: Int,
    minorTickSpacing: Int,
    onChange: Consumer<Int>
): JPanel {
    val result = JPanel()
    result.add(JLabel(label))
    val spinner = JSpinner(SpinnerNumberModel(defaultValue, min, max, minorTickSpacing))
    spinner.editor = JSpinner.NumberEditor(spinner, "#")
    val spinnerTextField = (spinner.editor as DefaultEditor).textField
    spinnerTextField.columns = 3
    spinner.addChangeListener { event: ChangeEvent ->
        val value = (event.source as JSpinner).value as Int
        onChange.accept(value)
    }
    result.add(spinner)
    return result
}