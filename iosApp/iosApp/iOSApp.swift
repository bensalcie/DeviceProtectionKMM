import SwiftUI
import ComposeApp

func setupKoin() {
    KoinInitializerKt.initKoin { _ in
        // no extra context needed
    }
}

@main
struct iOSApp: App {
    init() {
      setupKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}