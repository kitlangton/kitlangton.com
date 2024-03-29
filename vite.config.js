import {resolve} from 'path'
import {createHtmlPlugin} from 'vite-plugin-html'

const scalaVersion = '3.2.1'
// const scalaVersion = '3.0.0-RC3'

// https://vitejs.dev/config/
export default ({mode}) => {
    const mainJS = `./frontend/target/scala-${scalaVersion}/frontend-${mode === 'production' ? 'opt' : 'fastopt'}/main.js`
    const script = `<script type="module" src="${mainJS}"></script>`

    return {
        server: {
            proxy: {
                '/api': {
                    target: 'http://localhost:8088',
                    changeOrigin: true,
                    rewrite: (path) => path.replace(/^\/api/, '')
                },
            }
        },
        publicDir: './frontend/src/main/static/public',
        plugins: [
            createHtmlPlugin({
                minify: mode === 'production',
                inject: {
                    data: {
                        script
                    }
                }
            })
        ],
        resolve: {
            alias: {
                'stylesheets': resolve(__dirname, './frontend/src/main/static/stylesheets'),
            }
        }
    }
}
