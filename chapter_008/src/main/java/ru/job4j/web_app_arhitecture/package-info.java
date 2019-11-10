package ru.job4j.web_app_arhitecture;
/**
 * This example web app. It has three layer, every layer can
 * interact only with neighboring layer
 * <p>
 * model - defines shared pojo object
 * <p>
 * layers:
 * presentation - Define controllers and view. Layer interact with logic
 * logic -  Define business logic. Layer interact with presentation and persistent
 * persistent - Define storage. Layer interact with logic
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id
 * @since 0.1
 */