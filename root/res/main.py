from kivy.app import App
from kivy.lang import Builder
from kivy.uix.gridlayout import GridLayout
from kivymd.app import MDApp
from kivy.uix.screenmanager import ScreenManager, Screen
from kivymd.icon_definitions import md_icons
from kivy.uix.image import Image
from kivy.core.window import Window
from kivy.properties import ObjectProperty
from kivymd.uix.menu import MDDropdownMenu
from kivy.metrics import dp


class MainApp(MDApp):
    dropdown = ObjectProperty()

    def optionCallback(self, textOfTheOption):
        print(textOfTheOption)

    def build(self):
        menu_items = [
            {
                "viewclass": "OneLineListItem",
                "text": "Zaloguj się! ",
                "height": dp(56),
                "on_release": lambda x="Zaloguj się!": self.menu_callback(x),
            },
            {
                "viewclass": "OneLineListItem",
                "text": "Zarejestruj się!  ",
                "height": dp(56),
                "on_release": lambda x="Zarejestruj się! ": self.menu_callback(x),
            },

        ]
        self.menu = MDDropdownMenu(
            items=menu_items,
            width_mult=4
        )

        self.theme_cls.theme_style = "Light"
        self.theme_cls.primary_palette = "Red"
        self.theme_cls.accent_palette = "Red"
        return Builder.load_file('tbar.kv')

    def callback(self, button):
        self.menu.caller = button
        self.menu.open()


class FirstWindow(Screen):
    pass


class SecondWindow(Screen):
    pass


class MainScreen(GridLayout):
    manager = ObjectProperty(None)


class WindowManager(ScreenManager):
    pass


MainApp().run()
