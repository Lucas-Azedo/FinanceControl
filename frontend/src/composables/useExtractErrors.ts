export function useExtractErrors() {
  function extractErrors(data: any): string[] {
    const fieldErrors = data.fields && Object.values(data.fields)
    const arrayErrors = Array.isArray(data.errors) && data.errors
    const fallbackError = data.message || 'Erro desconhecido'
    return (fieldErrors || arrayErrors || [fallbackError]) as string[]
  }

  return { extractErrors }
}