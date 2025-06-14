import { ref } from 'vue'

type MessageType = 'error' | 'success' | null

interface GlobalMessageData {
  text: string
  type: MessageType
}

const globalMessage = ref<GlobalMessageData | null>(null)
let onClearCallback: (() => void) | null = null

export function useGlobalMessage() {
  function showMessage(text: string, type: MessageType = null, onClear?: () => void) {
    globalMessage.value = { text, type }
    onClearCallback = onClear || null
  }

  function clearMessage() {
    globalMessage.value = null
    if (onClearCallback) {
      onClearCallback()
      onClearCallback = null
    }
  }

  return {
    globalMessage,
    showMessage,
    clearMessage,
  }
}
