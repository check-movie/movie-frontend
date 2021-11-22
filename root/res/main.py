from kivy.app import App
from kivy.lang import Builder
from kivy.uix.floatlayout import FloatLayout
from kivy.uix.popup import Popup
from kivy.uix.widget import Widget
from kivymd.app import MDApp
from kivy.uix.screenmanager import ScreenManager, Screen
from kivy.uix.image import Image
from kivy.properties import ObjectProperty
from kivymd.uix.menu import MDDropdownMenu


class loginWindow(Screen):
    pass


class signupWindow(Screen):
    pass


class searchWindow(Screen):
    pass


class windowManager(ScreenManager):
    pass


kv = Builder.load_file('tbar.kv')
sm = windowManager()


# adding screens
sm.add_widget(searchWindow(name='search'))
sm.add_widget(loginWindow(name='login'))
sm.add_widget(signupWindow(name='signup'))

class MainApp(MDApp):
    def build(self):
        return sm



if __name__ == "__main__":
    MainApp().run()
