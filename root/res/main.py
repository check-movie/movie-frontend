from kivy.lang import Builder
from kivy.uix.floatlayout import FloatLayout
from kivy.uix.popup import Popup
from kivy.uix.widget import Widget
from kivymd.app import MDApp
from kivy.uix.screenmanager import ScreenManager, Screen
from kivy.uix.image import Image
from kivy.properties import ObjectProperty
from kivymd.uix.menu import MDDropdownMenu
import pandas as pd


class PopupWindow(Widget):
    def btn(self):
        popFun()


class P(FloatLayout):
    pass


def popFun():
    show = P()
    window = Popup(title="popup", content=show,
                   size_hint=(None, None), size=(300, 300))
    window.open()


class loginWindow(Screen):
    email = ObjectProperty(None)
    pwd = ObjectProperty(None)

    def validate(self):
        # validate, won't be used later in frontend
        if self.email.text not in users['Email'].unique():
            popFun()
        else:

            # switching the current screen to display validation result
            sm.current = 'logdata'

            # reset TextInput widget
            self.email.text = ""
            self.pwd.text = ""


class signupWindow(Screen):
    name2 = ObjectProperty(None)
    email = ObjectProperty(None)
    pwd = ObjectProperty(None)

    def signupbtn(self):

        # creating a DataFrame of the info
        user = pd.DataFrame([[self.name2.text, self.email.text, self.pwd.text]],
                            columns=['Name', 'Email', 'Password'])
        if self.email.text != "":
            if self.email.text not in users['Email'].unique():
                # if email does not exist already then append to the csv file
                # change current screen to log in the user now
                sm.current = 'login'
                self.name2.text = ""
                self.email.text = ""
                self.pwd.text = ""
        else:
            # if values are empty or invalid show pop up
            popFun()


class logDataWindow(Screen):
    pass

    def callback(self, button):
        self.menu.caller = button
        self.menu.open()

class searchWindow(Screen):
    pass


class windowManager(ScreenManager):
    pass


kv = Builder.load_file('tbar.kv')
sm = windowManager()


# adding screens
sm.add_widget(loginWindow(name='login'))
sm.add_widget(signupWindow(name='signup'))
sm.add_widget(logDataWindow(name='logdata'))
sm.add_widget(searchWindow(name='search'))


class MainApp(MDApp):
    def build(self):
        self.theme_cls.theme_style = "Light"
        self.theme_cls.primary_palette = "Red"
        self.theme_cls.accent_palette = "Red"
        return sm


if __name__ == "__main__":
    print('dupa')
    MainApp().run()
