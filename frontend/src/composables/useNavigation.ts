import { useRouter } from 'vue-router'

export function useNavigation() {
    const router = useRouter()

    function redirect(path: string) {
        router.push(path)
    }

    return { redirect }
}