export function useExtractErrors() {
  function extractErrors(data: any): string[] {
    if (!data || typeof data !== 'object') {
      return ['Erro desconhecido']
    }

    const fieldErrors = data.fields ? Object.values(data.fields) : null
    const arrayErrors = Array.isArray(data.errors) ? data.errors : null
    const fallbackError = typeof data.message === 'string' ? data.message : 'Erro desconhecido'

    return fieldErrors || arrayErrors || [fallbackError]
  }

  return { extractErrors }
}