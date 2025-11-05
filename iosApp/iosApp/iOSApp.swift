import SwiftUI
import ComposeApp
import ComposeApp

func setupKoin() {
    KoinInitializerKt.doInitKoin { _ in
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
