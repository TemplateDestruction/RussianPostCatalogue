package xyz.tusion.nrboom_app.presentation.view.base.fragment

/**
 * Интерфейс для реакции на нажатия на кнопку "назад"
 */
interface OnBackPressedListener {
    /**
     * Метод для обработки нажатия кнопки "назад"
     *
     * @return <code>true</code> если интерфейс "поглотил" событие нажатия
     *          <code>false</code> иначе
     */
    fun onBackPressed(): Boolean
}