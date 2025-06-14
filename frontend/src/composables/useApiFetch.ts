import { useGlobalMessage } from './useGlobalMessage'

interface ApiFetchOptions extends RequestInit {
  skipAuthInterceptor?: boolean
}

export async function useApiFetch<T = any>(url: string, options: ApiFetchOptions = {}): Promise<T | null> {
  const { showMessage } = useGlobalMessage()

  const defaultHeaders: HeadersInit = {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('token') || ''}`
  }

  try {
    const response = await fetch(url, {
      ...options,
      headers: {
        ...defaultHeaders,
        ...options.headers,
      }
    })

    const contentType = response.headers.get("content-type")

    if ((response.status === 401 || response.status === 403) && !options.skipAuthInterceptor) {
      showMessage(
        'Sessão expirada. Faça login novamente.',
        'error',
        () => { window.location.href = '/signin' }
      )
      return null
    }

    if (!response.ok) {
      let errorBody: any = {}

      if (contentType?.includes("application/json")) {
        errorBody = await response.json()
      } else {
        const errorText = await response.text()
        errorBody.message = errorText
      }

      throw errorBody
    }

    if (contentType?.includes("application/json")) {
      return await response.json()
    }

    return null

  } catch (err) {
    console.error('Erro em useApiFetch:', err)
    throw err
  }
}
